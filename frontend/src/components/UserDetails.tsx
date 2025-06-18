import "../assets/styles/userDetails.css";
import { useLocation, useNavigate } from "react-router-dom";
import { User } from "../models/UserType";
import { FormEvent, useState } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import useAuth from "../hooks/useAuth";
import { toast } from "sonner";

const UserDetails = () => {
    const { auth } = useAuth();
    const isAdmin: boolean = auth.roles.some((role) => role.name === "ADMIN");
    const location = useLocation();
    const navigate = useNavigate();
    const user: User = location.state;
    const axiosPrivate = useAxiosPrivate();
    const [firstName, setFirstName] = useState<string>(user.firstName);
    const [lastName, setLastName] = useState<string>(user.lastName);
    const [email, setEmail] = useState<string>(user.email);
    const [roles, setRoles] = useState<string[]>(user.roles);
    const [toDelete, setToDelete] = useState<boolean>(false);
    const [enabled, setEnabled] = useState<boolean>(user.enabled);
    const [newPass, setNewPass] = useState<string>("")

    const handleSubmit = async (
        event: FormEvent<HTMLFormElement>
    ): Promise<void> => {
        event.preventDefault();
        const controller = new AbortController();

        const updateUser = () => {
            return axiosPrivate.put(
                `/users/update/${email}`,
                JSON.stringify({
                    firstName,
                    lastName,
                    email,
                    roles,
                }),
                { signal: controller.signal }
            );
        };

        toast.promise(updateUser, {
            loading: "Cargando...",
            error: "Error!",
            success: `Datos de ${lastName}, ${firstName} actualizados`,
        });

        try {
            const response = await updateUser;
            console.log(response);
        } catch (error) {
            console.error(error);
        }
    };

    const handleDelete = async () => {
        const controller = new AbortController();
        const deleteUser = () =>
            axiosPrivate.delete(`/users/delete/${email}`, {
                signal: controller.signal,
            });

        toast.promise(deleteUser, {
            loading: "Cargando...",
            error: "Error!",
            success: `Usuario ${lastName}, ${firstName} eliminado exitosamente"`,
        });

        try {
            await deleteUser;
            navigate("/users");
        } catch (error) {
            console.error(error);
        }
    };

    const handleEnableUser = async () => {
        const controller = new AbortController();
        const enableUser = axiosPrivate.patch(
            `/users/toggle-enable/${email}`,
            {},
            { signal: controller.signal }
        );

        toast.promise(enableUser, {
            loading: "Cargando...",
            error: "Hubo un error",
            success: `Usuario ${lastName}, ${firstName} ${
                enabled ? "deshabilitado" : "restablecido"
            }`,
        });

        try {
            const response = await enableUser;
            setEnabled(response.data);
        } catch (error) {
            console.error(error);
        }
    }; 

    const handleUpdatePassword = async () => {
        const controller = new AbortController();
        const newPassword = axiosPrivate.put(
            `/users/${email}/password`,
            {password: newPass},
            { signal: controller.signal }
        );

        toast.promise(newPassword, {
            loading: "Cargando...",
            error: "Hubo un error",
            success: `Contraseña actualizada correctamente`,
        });

        try {
            const response = await newPassword;
            console.log(response.status);
        } catch (error) {
            console.error(error);
        }
    }

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
                        onChange={(e) => setRoles([e.target.value])}
                    >
                        <option value="USER">USER</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>
                </div>
                
                <div className="dflex gap-30">
                    <div>
                        <label htmlFor="newpass">Cambiar contraseña</label>
                        <input
                            type="password"
                            id="newpass"
                            value={newPass}
                            onChange={(e) => setNewPass(e.target.value)}
                        />
                    </div>
                    <button
                        className="green-btn alignself-end"
                        onClick={handleUpdatePassword}
                        type="button"
                    >
                        Actualizar contraseña
                    </button>
                </div>
                <div className="dflex fgrow gap-30">
                    <button
                        className="gold-btn fgrow"
                        onClick={() => navigate("/users")}
                        type="button"
                    >
                        Volver
                    </button>
                    {isAdmin ? (
                        <button
                            className="red-btn fgrow"
                            onClick={() => setToDelete(!toDelete)}
                            type="button"
                        >
                            Dar de baja
                        </button>
                    ) : (
                        <></>
                    )}
                    <button
                        type="button"
                        className={
                            enabled ? "transparent-red-btn" : "green-btn"
                        }
                        onClick={handleEnableUser}
                    >
                        {enabled ? "Deshabilitar" : "Restablecer"}
                    </button>
                    {isAdmin ? (
                        <button className="green-btn fgrow">Guardar</button>
                    ) : (
                        <></>
                    )}
                </div>
                {toDelete ? (
                    <button
                        className="red-btn"
                        type="button"
                        onClick={handleDelete}
                    >
                        Confirmar Eliminar definitivamente
                    </button>
                ) : (
                    <></>
                )}
            </form>
        </section>
    );
};

export default UserDetails;
