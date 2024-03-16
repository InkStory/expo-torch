import * as React from 'react';

import { ExpoTorchViewProps } from './ExpoTorch.types';

export default function ExpoTorchView(props: ExpoTorchViewProps) {
  return (
    <div>
      <span>{props.name}</span>
    </div>
  );
}
