package expo.modules.torch

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.kotlin.Promise
import expo.modules.kotlin.errors.CodedException

class ReactNativeTorchModule(val context: Context) : Module() {
  override fun definition() = ModuleDefinition {
    Name("ExpoTorch")

    Constants(
      "ON" to "ON",
      "OFF" to "OFF"
    )

    AsyncFunction("setStateAsync") { params: Map<String, Any>, promise: Promise ->
      val state = params["state"] as? String ?: run {
        promise.reject("E_INVALID_STATE", "The 'state' parameter is required and must be 'ON' or 'OFF'.", null)
        return@AsyncFunction
      }

      val cameraManager = context.applicationContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager
      try {
        for (cameraId in cameraManager.cameraIdList) {
          val characteristics = cameraManager.getCameraCharacteristics(cameraId)
          val hasFlash = characteristics.get(android.hardware.camera2.CameraCharacteristics.FLASH_INFO_AVAILABLE)
          val isBackCamera = characteristics.get(android.hardware.camera2.CameraCharacteristics.LENS_FACING) == android.hardware.camera2.CameraCharacteristics.LENS_FACING_BACK
          if (hasFlash == true && isBackCamera) {
            if (state == "ON") {
              cameraManager.setTorchMode(cameraId, true)
            } else if (state == "OFF") {
              cameraManager.setTorchMode(cameraId, false)
            } else {
              promise.reject("E_INVALID_STATE", "Invalid state. Use 'ON' or 'OFF'.", null)
              return@AsyncFunction
            }
            promise.resolve(null)
            return@AsyncFunction
          }
        }
        promise.reject("E_NO_FLASH", "No back-facing flash available.", null)
      } catch (e: CameraAccessException) {
        promise.reject("E_CAMERA_ACCESS", "Failed to access the camera for torch mode.", e)
      }
    }
  }
}
