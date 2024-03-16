import ExpoModulesCore
import AVFoundation

public class ExpoTorchModule: Module {
  private let TorchStateOn = "ON"
  private let TorchStateOff = "OFF"

  public func definition() -> ModuleDefinition {
    Name("ExpoTorch")

    Constants([
      "ON": self.TorchStateOn,
      "OFF": self.TorchStateOff
    ])

    AsyncFunction("setStateAsync") { (state: String, promise: Promise) in
      guard let device = AVCaptureDevice.default(for: .video), device.hasTorch else {
        promise.reject("E_TORCH_UNAVAILABLE", "Torch is not available on this device.")
        return
      }

      do {
        try device.lockForConfiguration()

        if state == self.TorchStateOn {
          try device.setTorchModeOn(level: AVCaptureDevice.maxAvailableTorchLevel)
        } else if state == self.TorchStateOff {
          device.torchMode = .off
        } else {
          promise.reject("E_INVALID_STATE", "Invalid state: \(state). Use 'ON' or 'OFF'.")
          return
        }

        device.unlockForConfiguration()
        promise.resolve(nil)
      } catch let error {
        device.unlockForConfiguration()
        promise.reject("E_TORCH_FAILURE", "Failed to set torch state: \(error)")
      }
    }
  }
}
