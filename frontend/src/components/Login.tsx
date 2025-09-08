import { useEffect, useRef, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "../api/axios";
import "../assets/styles/login.css";
import { AxiosError } from "axios";
import useAuthStore from "../hooks/useAuthStore";

const LOGIN_URL = "/auth/login";

const Login = () => {
  const setAuth = useAuthStore(state => state.setAuth);

  const emailRef = useRef<HTMLInputElement>(null);
  const errRef = useRef<HTMLInputElement>(null);

  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || "/";

  const [email, setEmail] = useState("");
  const [pwd, setPwd] = useState("");
  const [errMsg, setErrMsg] = useState("");

  useEffect(() => {
    emailRef.current?.focus();
  }, []);

  useEffect(() => {
    setErrMsg("");
  }, [email, pwd]);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        LOGIN_URL,
        { email: email, password: pwd },
        {
          headers: { "Content-Type": "application/json" },
          withCredentials: true,
        }
      );

      const { roleSet, accessToken } = response.data;
      setAuth({ email, roleSet, accessToken });
      setEmail("");
      setPwd("");
      navigate(from, { replace: true });
    } catch (err) {
      const error = err as AxiosError;
      if (!error.response) {
        setErrMsg("Sin respuesta del servidor");
      } else if (error.response.status === 400) {
        setErrMsg("Usuario o Contrasena incorrecta.");
      } else if (error.response.status === 401) {
        setErrMsg("No autorizado");
      } else {
        setErrMsg("Ingreso fallido");
      }
      errRef.current?.focus();
    }
  };

  return (
    <section className="login-container">
      <section className="login">
        <p
          ref={errRef}
          className={errMsg ? "errmsg" : "offscreen"}
          aria-live="assertive"
        >
          {errMsg}
        </p>
        <h1>Ingresar</h1>
        <form onSubmit={handleSubmit}>
          {/* Usuario */}
          <div className="label-input">
            <label htmlFor="email">Email</label>
            <input
              type="text"
              id="email"
              ref={emailRef}
              onChange={(e) => setEmail(e.target.value)}
              value={email}
              autoComplete="on"
              required
            />
          </div>
          {/* Contrasena */}
          <div className="label-input">
            <label htmlFor="password">Contraseña</label>
            <input
              type="password"
              id="password"
              onChange={(e) => setPwd(e.target.value)}
              value={pwd}
              required
            />
          </div>
          <button className="red-btn">Ingresar</button>
        </form>
      </section>
    </section>
  );
};

export default Login;
