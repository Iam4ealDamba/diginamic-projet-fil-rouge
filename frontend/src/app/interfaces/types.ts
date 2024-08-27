import { IconDefinition } from '@fortawesome/free-solid-svg-icons';

type role = 'USER' | 'MANAGER' | 'ADMIN';

// SidebarMenuType
export type SidebarMenuType = {
  top: {
    icon: IconDefinition;
    link: string;
    name: string;
    active: boolean;
  }[];
  bottom: {
    icon: IconDefinition;
    link: string;
    name: string;
    active: boolean;
    onclick?: () => void;
  }[];
};

export type UserType = {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  birthDate: Date;
  role?: role;
};

// ------------- DTO -------------
export type UserLoginDTOType = {
  email: string | null;
  password: string | null;
};

export type UserRegisterDTOType = {
  email: string | null;
  password: string | null;
  firstname: string | null;
  lastname: string | null;
  role?: 'USER';
};
