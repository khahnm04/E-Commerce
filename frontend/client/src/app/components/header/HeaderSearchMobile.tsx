"use client";
import React, { useState } from 'react';
import { useRouter } from 'next/navigation';

interface HeaderSearchMobileProps {
  onClose: () => void;
}

export const HeaderSearchMobile: React.FC<HeaderSearchMobileProps> = ({ onClose }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const router = useRouter();

  const handleSearch = () => {
    if (searchTerm.trim()) {
      router.push(`/product?search=${searchTerm}`);
      onClose(); // Close mobile menu after search
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      handleSearch();
    }
  };

  return (
    <div className="flex items-center w-full relative">
      <input
        type="text"
        placeholder="Tìm kiếm..."
        className="flex-grow p-2 rounded-lg border-none focus:outline-none focus:ring-2 focus:ring-red-500 text-gray-800"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        onKeyPress={handleKeyPress}
      />
      <button
        onClick={handleSearch}
        className="absolute right-0 top-0 bottom-0 px-3 bg-red-600 hover:bg-red-700 text-white rounded-r-lg"
      >
        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
        </svg>
      </button>
    </div>
  );
};
