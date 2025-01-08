import { Link, useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { User } from "../../models/UserType";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import axios from "axios";
import "../../assets/styles/users.css";

const Users = () => {
    const [users, setUsers] = useState<User[]>([]);
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();

        const getUsers = async () => {
            try {
                const response = await axiosPrivate.get("/users", {
                    signal: controller.signal,
                });
                console.log(response.data);
                if(isMounted){
                    setUsers(response.data);
                }
            } catch (err) {
                if (axios.isCancel(err)) {
                    console.error("Request canceled", err.message);
                } else {
                    console.error("Non-cancel error:", err); // Logs non-cancel errors
                    navigate("/login", {
                        state: { from: location },
                        replace: true,
                    });
                }
            }
        };

        getUsers();

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, [axiosPrivate, location, navigate]);

    return (
        <section className="users">
            <div>
            <h3>Usuarios</h3>
                <Link to="/users/create">
                    <button className="green-btn add">Nuevo Usuario</button>
                </Link>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Email</th>
                        <th>Rol</th>
                        <th>Accion</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map((user) => (
                        <tr key={user.email}>
                            <td>{user.firstName}</td>
                            <td>{user.lastName}</td>
                            <td>{user.email}</td>
                            <td>{user.roles[0]}</td>
                            <td>
                                <Link to="/users/detail" state={user}>
                                    <button>Editar</button>
                                </Link>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </section>
    );
};

export default Users;
