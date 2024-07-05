import "./App.css";
import { useState } from "react";
import Login from "./Login";
import Register from "./Register";

function App() {
  const [isLogin, setIsLogin] = useState(true);

  return (
    <>
      <h1>Online Quiz Application</h1>
      <button onClick={() => setIsLogin(true)}>Login</button>
      <button onClick={() => setIsLogin(false)}>Register</button>
      {isLogin ? <Login /> : <Register />}
    </>
  );
}

export default App;
