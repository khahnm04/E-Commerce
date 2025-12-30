/* eslint-disable react-hooks/exhaustive-deps */
"use client";
import { useEffect, useRef } from "react";
import * as FilePond from "filepond";
import "filepond/dist/filepond.min.css";
import FilePondPluginImagePreview from "filepond-plugin-image-preview";
import FilePondPluginFileValidateType from "filepond-plugin-file-validate-type";
import "filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css";

FilePond.registerPlugin(
  FilePondPluginImagePreview,
  FilePondPluginFileValidateType
);

interface FilePondWrapperProps {
  id?: string;
  name: string;
  accept?: string;
  className?: string;
  onFileChange?: (files: File[]) => void;
}

export const FilePondWrapper = ({
  id,
  name,
  accept,
  className,
  onFileChange,
}: FilePondWrapperProps) => {
  const inputRef = useRef<HTMLInputElement | null>(null);
  const pondRef = useRef<FilePond.FilePond | null>(null);
  const initializedRef = useRef(false); // chống StrictMode

  useEffect(() => {
    if (!inputRef.current) return;
    if (initializedRef.current) return;

    initializedRef.current = true;

    pondRef.current = FilePond.create(inputRef.current, {
      labelIdle: "+",
      acceptedFileTypes: accept ? accept.split(",") : [],
      storeAsFile: true,
      onupdatefiles: (fileItems) => {
        const files = fileItems
          .map((item) => item.file)
          .filter(Boolean) as File[];

        onFileChange?.(files);
      },
    });

    return () => {
      if (pondRef.current) {
        pondRef.current.destroy();
        pondRef.current = null;
        initializedRef.current = false;
      }
    };
  }, []);

  return (
    <div className={className}>
      <input
        ref={inputRef}
        type="file"
        name={name}
        id={id}
        accept={accept}
      />
    </div>
  );
};
