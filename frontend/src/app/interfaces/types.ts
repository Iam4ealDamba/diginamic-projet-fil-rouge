import { IconDefinition } from '@fortawesome/free-solid-svg-icons';

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
  firstname: string;
  lastname: string;
  birthdate: Date;
  roles: string;
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
