import { NativeModulesProxy, EventEmitter, Subscription } from 'expo-modules-core';

// Import the native module. On web, it will be resolved to ExpoTorch.web.ts
// and on native platforms to ExpoTorch.ts
import ExpoTorchModule from './ExpoTorchModule';
import ExpoTorchView from './ExpoTorchView';
import { ChangeEventPayload, ExpoTorchViewProps } from './ExpoTorch.types';

// Get the native constant value.
export const PI = ExpoTorchModule.PI;

export function hello(): string {
  return ExpoTorchModule.hello();
}

export async function setValueAsync(value: string) {
  return await ExpoTorchModule.setValueAsync(value);
}

const emitter = new EventEmitter(ExpoTorchModule ?? NativeModulesProxy.ExpoTorch);

export function addChangeListener(listener: (event: ChangeEventPayload) => void): Subscription {
  return emitter.addListener<ChangeEventPayload>('onChange', listener);
}

export { ExpoTorchView, ExpoTorchViewProps, ChangeEventPayload };
