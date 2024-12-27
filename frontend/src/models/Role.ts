type Role = {
    id: number;
    name: string;
    permissions: { id: number; permission: string };
};

export default Role;