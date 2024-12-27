import { useEffect, useRef, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "../api/axios";
import "../assets/styles/login.css";
import useAuth from "../hooks/useAuth";

const LOGIN_URL = "/auth/login";

const Login = () => {
    const { setAuth } = useAuth();

    const emailRef = useRef(null);
    const errRef = useRef(null);

    const navigate = useNavigate();
    const location = useLocation();
    const from = location.state?.from?.pathname || "/";

    const [email, setEmail] = useState("");
    const [pwd, setPwd] = useState("");
    const [errMsg, setErrMsg] = useState("");

    useEffect(() => {
        emailRef.current.focus();
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

            const accessToken = response?.data?.accessToken;
            const roles = response?.data?.roleSet;
            setAuth({ email, roles, accessToken });
            setEmail("");
            setPwd("");
            navigate(from, { replace: true });
        } catch (err) {
            if (!err?.response) {
                setErrMsg("Sin respuesta del servidor");
            } else if (err.response?.status === 400) {
                setErrMsg("Usuario o Contrasena incorrecta.");
            } else if (err.response?.status === 401) {
                setErrMsg("No autorizado");
            } else {
                setErrMsg("Ingreso fallido");
            }
            errRef.current.focus();
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
