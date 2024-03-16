import ExpoTorchModule from "./ExpoTorchModule";

export const { ON, OFF } = ExpoTorchModule;

export async function setStateAsync(state: string) {
  return ExpoTorchModule.setStateAsync(state);
}
