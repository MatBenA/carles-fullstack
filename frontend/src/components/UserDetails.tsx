import "../assets/styles/userDetails.css";
import { Link, useLocation } from "react-router-dom";
import { User } from "../models/UserType";
import { FormEvent, useState } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

const UserDetails = () => {
    const location = useLocation();

    const user: User = location.state;
    const axiosPrivate = useAxiosPrivate();
    const [firstName, setFirstName] = useState<string>(user.firstName);
    const [lastName, setLastName] = useState<string>(user.lastName);
    const [email, setEmail] = useState<string>(user.email);
    const [roles, setRoles] = useState<string[]>(user.roles);

    const handleSubmit = async (
        event: FormEvent<HTMLFormElement>
    ): Promise<void> => {
        event.preventDefault();

        try {
            const response = await axiosPrivate.put(
                `/users/update/${email}`,
                JSON.stringify({
                    firstName,
                    lastName,
                    email,
                    roles,
                })
            );

            console.log(response.data);
        } catch (error) {
            console.error(error);
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
                            id="firstName"
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                        />
                    </div>
                    <div>
                        <label htmlFor="lastName">Apellido</label>
                        <input
                            type="text"
                            id="lastName"
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                        />
                    </div>
                </div>
                <div>
                    <label htmlFor="email">Email</label>
                    <input
                        type="email"
                        value={email}
                        id="email"
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
                <div>
                    <label htmlFor="role">Rol</label>
                    <select
                        name="role"
                        id="role"
                        value={roles[0]}
                        onChange={(e) => setRoles([...roles, e.target.value])}
                    >
                        <option value="USER">USER</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>
                </div>
                <div className="dflex fgrow gap-30">
                    <Link to="/users">
                        <button className="gold-btn">Volver</button>
                    </Link>
                    <button className="green-btn">Guardar</button>
                </div>
            </form>
        </section>
    );
};

export default UserDetails;
