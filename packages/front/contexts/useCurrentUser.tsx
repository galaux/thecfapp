import React from 'react';
import { IUser } from 'types/user';

type CurrentUserContextType = {
  currentUser: IUser;
  setCurrentUser: React.Dispatch<React.SetStateAction<IUser>>;
};

const CurrentUserContext = React.createContext<CurrentUserContextType>({
  currentUser: null,
  setCurrentUser: () => null,
});

export const useCurrentUser = (): CurrentUserContextType => {
  const context = React.useContext(CurrentUserContext);
  if (!context)
    throw new Error(`useCurrentUser must be used within a CurrentUserProvider`);
  return context;
};

export const CurrentUserProvider: React.FC = ({ children }) => {
  const [currentUser, setCurrentUser] = React.useState<IUser>();

  return (
    <CurrentUserContext.Provider value={{ currentUser, setCurrentUser }}>
      {children}
    </CurrentUserContext.Provider>
  );
};
