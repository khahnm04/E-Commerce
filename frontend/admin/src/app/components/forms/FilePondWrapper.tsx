"use client";
import { useEffect, useRef } from 'react';
import * as FilePond from 'filepond';
import 'filepond/dist/filepond.min.css';
import FilePondPluginImagePreview from 'filepond-plugin-image-preview';
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css';

FilePond.registerPlugin(
  FilePondPluginImagePreview,
  FilePondPluginFileValidateType
);

interface FilePondWrapperProps {
  id?: string;
  name: string;
  accept?: string;
  className?: string; // Thêm className để linh hoạt styling
  // Có thể thêm defaultValue nếu muốn load ảnh cũ
}

export const FilePondWrapper = ({ id, name, accept, className }: FilePondWrapperProps) => {
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (inputRef.current) {
      const instance = FilePond.create(inputRef.current, {
        labelIdle: "+", // Có thể thay bằng icon hoặc text tùy chỉnh
        acceptedFileTypes: accept ? accept.split(',') : [],
        storeAsFile: true, // Quan trọng: Giúp form lấy được file object thay vì base64
      });

      return () => {
        instance.destroy();
      };
    }
  }, [accept]);

  return (
    <div className={className}>
      <input
        type="file"
        name={name}
        id={id}
        ref={inputRef}
        accept={accept}
      />
    </div>
  );
};