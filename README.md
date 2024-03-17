# expo-torch

Module to control torch (flashlight) on Android & iOS.

# API documentation

- [Documentation for the main branch](https://github.com/expo/expo/blob/main/docs/pages/versions/unversioned/sdk/torch.md)
- [Documentation for the latest stable release](https://docs.expo.dev/versions/latest/sdk/torch/)

# Installation in managed Expo projects

For [managed](https://docs.expo.dev/archive/managed-vs-bare/) Expo projects, please follow the installation instructions in the [API documentation for the latest stable release](#api-documentation). If you follow the link and there is no documentation available then this library is not yet usable within managed projects &mdash; it is likely to be included in an upcoming Expo SDK release.

# Installation in bare React Native projects

For bare React Native projects, you must ensure that you have [installed and configured the `expo` package](https://docs.expo.dev/bare/installing-expo-modules/) before continuing.

### Add the package to your npm dependencies

```
npm install expo-torch
```

### Configure for iOS

Run `npx pod-install` after installing the npm package.


### Configure for Android

# Usage

```tsx
import { StyleSheet, View, Button } from "react-native";
import * as ExpoTorch from "expo-torch";

export default function App() {
    const on = async () => {
        await ExpoTorch.setStateAsync(ExpoTorch.ON);
    };

    const off = async () => {
        await ExpoTorch.setStateAsync(ExpoTorch.OFF);
    };

    return (
        <View style={styles.container}>
            <Button title="ON" onPress={on} />
            <Button title="OFF" onPress={off} />
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#fff",
        alignItems: "center",
        justifyContent: "center",
    },
});
```

# Contributing

Contributions are very welcome! Please refer to guidelines described in the [contributing guide]( https://github.com/expo/expo#contributing).
