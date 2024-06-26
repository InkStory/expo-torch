package expo.modules.torch

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.kotlin.Promise
import android.util.Log

class ExpoTorchModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("ExpoTorch")

    Constants(
      "ON" to "ON",
      "OFF" to "OFF"
    )

    AsyncFunction("setStateAsync") { state: String, promise: Promise ->
      val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as? CameraManager ?: run {
        promise.reject("E_CAMERA_MANAGER_UNAVAILABLE", "CameraManager is not available.", null)
        return@AsyncFunction
      }

      if (state != "ON" && state != "OFF") {
        promise.reject("E_INVALID_STATE", "Invalid state: $state. Use 'ON' or 'OFF'.", null)
        return@AsyncFunction
      }

      try {
        for (cameraId in cameraManager.cameraIdList) {
          val characteristics = cameraManager.getCameraCharacteristics(cameraId)
          val hasFlash = characteristics.get(android.hardware.camera2.CameraCharacteristics.FLASH_INFO_AVAILABLE) ?: false
          val isBackCamera = characteristics.get(android.hardware.camera2.CameraCharacteristics.LENS_FACING) == android.hardware.camera2.CameraCharacteristics.LENS_FACING_BACK
          if (hasFlash && isBackCamera) {
            try {
              cameraManager.setTorchMode(cameraId, state == "ON")
              promise.resolve(null)
              return@AsyncFunction
            } catch (e: CameraAccessException) {
              if (e.reason == CameraAccessException.CAMERA_IN_USE) {
                Log.e("ExpoTorchModule", "Torch is not available because the camera is in use by another application.")
                promise.reject("E_CAMERA_IN_USE", "Torch is not available because the camera is in use.", e)
              } else {
                promise.reject("E_TORCH_FAILURE", "Failed to set torch state: ${e.message}", e)
              }
              return@AsyncFunction
            }
          }
        }
        promise.reject("E_TORCH_UNAVAILABLE", "Torch is not available on this device.", null)
      } catch (e: CameraAccessException) {
        promise.reject("E_TORCH_FAILURE", "Failed to access camera characteristics: ${e.message}", e)
      }
    }
  }

  private val context
    get() = requireNotNull(appContext.reactContext) { "Context is not available." }
}
