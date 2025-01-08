export interface User{
    firstName: string;
    lastName: string;
    email: string;
    enabled: boolean;
    roles: Array<string>;
}