import "./App.css";
import { useState } from "react";
import Login from "./components/Login";
import Register from "./components/Register";
import Header from "./components/Header";

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
