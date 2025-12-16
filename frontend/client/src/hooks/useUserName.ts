import { useEffect, useState } from 'react';

export const useUserName = () => {
  const [userName, setUserName] = useState<string | null>(null);

  const getLastName = (fullName: string): string => {
    const nameParts = fullName.trim().split(' ');
    return nameParts[nameParts.length - 1] || fullName;
  };

  useEffect(() => {
    const storedFullName = localStorage.getItem('userFullName');
    if (storedFullName) {
      const lastName = getLastName(storedFullName);
      setUserName(lastName);
    } else {
      setUserName(null);
    }

    const handleStorageChange = () => {
      const updatedFullName = localStorage.getItem('userFullName');
      if (updatedFullName) {
        const lastName = getLastName(updatedFullName);
        setUserName(lastName);
      } else {
        setUserName(null);
      }
    };

    const handleUserUpdate = () => {
      const updatedFullName = localStorage.getItem('userFullName');
      if (updatedFullName) {
        const lastName = getLastName(updatedFullName);
        setUserName(lastName);
      } else {
        setUserName(null);
      }
    };

    window.addEventListener('storage', handleStorageChange);
    window.addEventListener('userUpdated', handleUserUpdate);

    return () => {
      window.removeEventListener('storage', handleStorageChange);
      window.removeEventListener('userUpdated', handleUserUpdate);
    };
  }, []);

  return userName;
};

