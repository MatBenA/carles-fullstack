import { FormEvent, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { toast } from "sonner";
import axios from "axios";

const UserCreate = () => {
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();

  const [firstName, setFirstName] = useState<string>("");
  const [lastName, setLastName] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [role, setRole] = useState<string>("USER");
  const [password, setPassword] = useState<string>("");
  const [matchPwd, setMatchPwd] = useState<string>("");

  const handleSubmit = async (
    event: FormEvent<HTMLFormElement>
  ): Promise<void> => {
    event.preventDefault();
    if (password !== matchPwd) {
      toast.error("Las contraseñas deben coincidir, intentelo de nuevo.");
      return;
    }
    const controller = new AbortController();
    const createUser = axiosPrivate.post(
      "/users/create",
      JSON.stringify({
        firstName,
        lastName,
        email,
        role,
        password,
      }),
      {
        signal: controller.signal,
      }
    );

    toast.promise(createUser, {
      loading: "Cargando...",
      error: "Error!",
      success: `Usuario ${lastName}, ${firstName} creado exitosamente`,
    });

    try {
      const response = await createUser;
      console.log(response.data);
      navigate("/users");
    } catch (error) {
      if (axios.isCancel(error)) {
        return;
      } else {
        console.error(error);
      }
    }
  };

  return (
    <section className="user-details">
      <form onSubmit={handleSubmit}>
        <div className="dflex gap-30">
          <div>
            <label htmlFor="firstName">Nombre</label>
            <input
              type="text"
              id="fistName"
              autoComplete="off"
              onChange={(e) => setFirstName(e.target.value)}
              value={firstName}
              required
              aria-describedby="uidnote"
              autoFocus
            />
          </div>
          <div>
            <label htmlFor="lastName">Apellido</label>
            <input
              type="text"
              id="lastName"
              autoComplete="off"
              onChange={(e) => setLastName(e.target.value)}
              value={lastName}
              required
              aria-describedby="uidnote"
            />
          </div>
        </div>
        <div>
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            autoComplete="off"
            onChange={(e) => setEmail(e.target.value)}
            value={email}
            required
            aria-describedby="uidnote"
          />
        </div>
        <div>
          <label htmlFor="rol">Rol</label>
          <select
            name="rol"
            id="rol"
            defaultValue="USER"
            onChange={(e) => setRole(e.target.value)}
          >
            <option value="USER">USER</option>
            <option value="ADMIN">ADMIN</option>
          </select>
        </div>
        <div>
          <label htmlFor="password">Contraseña</label>
          <input
            type="password"
            id="password"
            onChange={(e) => setPassword(e.target.value)}
            value={password}
            required
          />
        </div>
        <div>
          <label htmlFor="matchPwd">Repetir Contrasena</label>
          <input
            type="password"
            id="matchPwd"
            onChange={(e) => setMatchPwd(e.target.value)}
            value={matchPwd}
            required
          />
        </div>
        <div className="dflex fgrow gap-30">
          <Link to="/users">
            <button className="gold-btn" type="button">
              Volver
            </button>
          </Link>
          <button className="green-btn f-stretch">Guardar</button>
        </div>
      </form>
    </section>
  );
};

export default UserCreate;
