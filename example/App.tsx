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
