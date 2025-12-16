import { useEffect } from 'react';
import * as FilePond from 'filepond';
import 'filepond/dist/filepond.min.css';
import FilePondPluginImagePreview from 'filepond-plugin-image-preview';
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css';

// Đăng ký plugin một lần duy nhất
let isRegistered = false;

export const useFilePond = () => {
  useEffect(() => {
    // Chỉ đăng ký plugin 1 lần
    if (!isRegistered) {
      FilePond.registerPlugin(
        FilePondPluginImagePreview,
        FilePondPluginFileValidateType
      );
      isRegistered = true;
    }

    // Khởi tạo FilePond cho tất cả elements
    const listFileponeImage = document.querySelectorAll("[filepond-image]");

    const instances: any[] = [];

    if (listFileponeImage.length > 0) {
      listFileponeImage.forEach(fileponeImage => {
        const instance = FilePond.create(fileponeImage, {
          labelIdle: "+"
        });
        instances.push(instance);
      });
    }

    // Cleanup khi component unmount
    return () => {
      instances.forEach(instance => {
        instance.destroy();
      });
    };
  }, []);
};
