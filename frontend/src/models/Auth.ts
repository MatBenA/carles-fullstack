import Role from "./Role";

type Auth = {
    email: string;
    roles: Role[];
    accessToken: string;
};

export default Auth;