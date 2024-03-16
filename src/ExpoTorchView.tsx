import { requireNativeViewManager } from 'expo-modules-core';
import * as React from 'react';

import { ExpoTorchViewProps } from './ExpoTorch.types';

const NativeView: React.ComponentType<ExpoTorchViewProps> =
  requireNativeViewManager('ExpoTorch');

export default function ExpoTorchView(props: ExpoTorchViewProps) {
  return <NativeView {...props} />;
}
