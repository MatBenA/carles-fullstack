import { Link } from "react-router-dom";

const Missing = () => {
    return (
        <article>
            <h1>Ooops!</h1>

            <p>Pagina no encontrada</p>

            <div className="flexGrow">
                <Link to="/">Visita nuestra Pagina de Inicio</Link>
            </div>
        </article>
    )
}
export default Missing;