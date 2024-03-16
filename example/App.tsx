import { StyleSheet, View, Button } from "react-native";

import * as ExpoTorch from "expo-torch";

export default function App() {
  const on = async () => {
    try {
      await ExpoTorch.setStateAsync(ExpoTorch.ON);
    } catch (error) {
      console.error(error);
    }
  };

  const off = async () => {
    try {
      await ExpoTorch.setStateAsync(ExpoTorch.OFF);
    } catch (error) {
      console.error(error);
    }
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
