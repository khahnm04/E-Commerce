"use client";

import { useRef, ReactNode } from "react";
import dynamic from "next/dynamic";
import type { Editor as TinyMCEEditor } from "tinymce";

const Editor = dynamic(
  () => import("@tinymce/tinymce-react").then((m) => m.Editor),
  { ssr: false }
);

interface UseTinyMCEProps {
  initialValue?: string;
  height?: number;
  onChange?: (content: string) => void;
}

// Định nghĩa kiểu trả về
interface UseTinyMCEReturn {
  TinyMCEEditorComponent: ReactNode;
  editorRef: React.MutableRefObject<TinyMCEEditor | null>;
}

export function useTinyMCE({
  initialValue = "",
  height = 300,
  onChange,
}: UseTinyMCEProps = {}): UseTinyMCEReturn {
  const editorRef = useRef<TinyMCEEditor | null>(null);

  const TinyMCEEditorComponent = (
    <Editor
      apiKey={process.env.NEXT_PUBLIC_API_KEY_TINYMCE}
      onInit={(_, editor) => (editorRef.current = editor)}
      initialValue={initialValue}
      onEditorChange={(content) => {
        if (onChange) onChange(content);
      }}
      init={{
        height,
        menubar: false,
        plugins: [
          // List plugins giữ nguyên
          "advlist", "autolink", "lists", "link", "image", "charmap",
          "preview", "anchor", "searchreplace", "visualblocks", "code",
          "fullscreen", "insertdatetime", "media", "table", "code",
          "help", "wordcount",
        ],
        toolbar:
          "undo redo | blocks | bold italic forecolor | " +
          "alignleft aligncenter alignright alignjustify | " +
          "bullist numlist outdent indent | removeformat | help",
        content_style:
          "body { font-family:Helvetica,Arial,sans-serif; font-size:14px; }",
      }}
    />
  );

  return { TinyMCEEditorComponent, editorRef };
}