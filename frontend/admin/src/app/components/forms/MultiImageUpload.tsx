"use client";

import React, { useRef, useState, useEffect, useCallback } from "react";
import Image from "next/image"; // Assuming Next.js Image component for optimization

interface MultiImageUploadProps {
  initialImages?: File[]; // Optional: for editing existing products
  onImagesChange: (images: File[]) => void;
}

const MultiImageUpload: React.FC<MultiImageUploadProps> = ({
  initialImages = [],
  onImagesChange,
}) => {
  const fileInputRef = useRef<HTMLInputElement>(null);
  const [images, setImages] = useState<File[]>(initialImages);
  const [imagePreviews, setImagePreviews] = useState<
    { file: File; url: string }[]
  >([]);

  // Effect to create and revoke object URLs for image previews
  useEffect(() => {
    const newPreviews = images.map((file) => ({
      file,
      url: URL.createObjectURL(file),
    }));
    setImagePreviews(newPreviews);

    // Revoke object URLs on component unmount or when images change
    return () => {
      newPreviews.forEach((preview) => URL.revokeObjectURL(preview.url));
    };
  }, [images]);

  // Notify parent component about image changes
  useEffect(() => {
    onImagesChange(images);
  }, [images, onImagesChange]);

  const handleFileSelect = useCallback((event: React.ChangeEvent<HTMLInputElement>) => {
    const selectedFiles = event.target.files;
    if (selectedFiles && selectedFiles.length > 0) {
      // Convert FileList to an array and append to existing images
      const newFiles = Array.from(selectedFiles);
      setImages((prevImages) => [...prevImages, ...newFiles]);
    }
    // Clear the input value to allow selecting the same file(s) again
    if (fileInputRef.current) {
      fileInputRef.current.value = "";
    }
  }, []);

  const handleAddButtonClick = useCallback(() => {
    fileInputRef.current?.click();
  }, []);

  const handleRemoveImage = useCallback((indexToRemove: number) => {
    setImages((prevImages) =>
      prevImages.filter((_, index) => index !== indexToRemove)
    );
  }, []);

  return (
    <div>
      <input
        type="file"
        multiple
        accept="image/*"
        ref={fileInputRef}
        onChange={handleFileSelect}
        className="hidden" // Hide the actual file input
      />

      {imagePreviews.length === 0 ? (
        // UI for when no images are selected
        <button
          type="button"
          onClick={handleAddButtonClick}
          className="relative w-full h-[148px] p-8 rounded-lg border-2 border-dashed border-gray-300 flex flex-col items-center justify-center text-gray-400 hover:border-blue-500 hover:text-blue-500 transition-colors hover:shadow-md transition-all duration-300 transform hover:scale-102 cursor-pointer"
          aria-label="Thêm ảnh sản phẩm"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="h-16 w-16 mb-2"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M12 4v16m8-8H4"
            />
          </svg>
          <span className="text-lg font-medium">Thêm ảnh sản phẩm khác</span>
          <span className="text-sm">Chấp nhận định dạng: JPG, PNG, GIF</span>
        </button>
      ) : (
        // UI for when images are selected
        <div className="flex flex-wrap gap-4"> {/* Changed to flex wrap for better fixed-size item layout */}
          {imagePreviews.map((preview, index) => (
            <div
              key={preview.url} // Using URL as key, safe as it's unique per object URL
              className="relative w-[148px] h-[148px] rounded-lg overflow-hidden border border-gray-200 group shadow-sm hover:shadow-md transition-all duration-300 transform hover:scale-102"
            >
              <Image
                src={preview.url}
                alt={`Product preview ${index + 1}`}
                layout="fill"
                objectFit="cover"
                className="absolute inset-0"
              />
              <button
                type="button"
                onClick={() => handleRemoveImage(index)}
                className="absolute top-1 right-1 bg-red-500 text-white rounded-full p-1 text-xs leading-none flex items-center justify-center w-6 h-6 opacity-0 group-hover:opacity-100 transition-opacity hover:bg-red-700"
                aria-label="Remove image"
              >
                &times;
              </button>
            </div>
          ))}

          {/* Add new image button - when images exist */}
          <button
            type="button"
            onClick={handleAddButtonClick}
            className="relative w-[148px] h-[148px] rounded-lg border-2 border-dashed border-gray-300 flex items-center justify-center text-gray-400 hover:border-blue-500 hover:text-blue-500 transition-colors hover:shadow-md transition-all duration-300 transform hover:scale-102 cursor-pointer"
            aria-label="Add new image"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="h-8 w-8"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M12 4v16m8-8H4"
              />
            </svg>
          </button>
        </div>
      )}
    </div>
  );
};

export default MultiImageUpload;
