"use client"
import { AppButton } from "@/app/components/auth/AppButton";
import { InputField } from "@/app/components/auth/InputField";
import { SocialButton } from "@/app/components/auth/SocialButton";
import JustValidate from "just-validate";
import Link from "next/link";
import { useEffect, useRef } from "react";
import { useRouter } from "next/navigation";

export default function LoginPage() {
  const validatorRef = useRef<any>(null);
  const router = useRouter();

  const inputFields = [
    {
      id: "phoneNumber",
      label: "Số điện thoại",
      type: "text",
      placeholder: "Nhập số điện thoại của bạn"
    },
    {
      id: "password",
      label: "Mật khẩu",
      type: "password",
      placeholder: "Nhập mật khẩu của bạn"
    }
  ];

  const socialButtons = [
    {
      icon: "/assets/images/icon-google.jpg",
      label: "Google"
    },
    {
      icon: "/assets/images/icon-facebook.jpg",
      label: "Facebook"
    }
  ];

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      if (validatorRef.current) return;

      const validator = new JustValidate("#loginForm", {
        errorLabelStyle: {
          position: 'absolute',
          top: '104%',
          left: '0',
          width: '100%',
          fontSize: '12px',
          color: '#e63946'
        },
        validateBeforeSubmitting: true,
      });

      // Xóa lỗi cũ nếu có
      document.querySelectorAll(".just-validate-error-label").forEach(el => el.remove());

      validator
        .addField("#phoneNumber", [
          {
            rule: "required",
            errorMessage: "Vui lòng nhập số điện thoại!"
          },
          {
            rule: "customRegexp",
            value: /^(0|\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-6|8|9]|9[0-4|6-9])[0-9]{7}$/,
            errorMessage: "Số điện thoại không đúng định dạng!"
          }
        ])
        .addField('#password', [
          {
            rule: "required",
            errorMessage: "Vui lòng nhập mật khẩu của bạn!"
          },
          {
            rule: "minLength",
            value: 8,
            errorMessage: "Mật khẩu phải chứa ít nhất 8 ký tự!"
          },
          {
            validator: (value: string) => {
              const regex = /[A-Z]/;
              return regex.test(value);
            },
            errorMessage: "Mật khẩu phải chứa ký tự viết hoa!"
          },
          {
            validator: (value: string) => {
              const regex = /[a-z]/;
              return regex.test(value);
            },
            errorMessage: "Mật khẩu phải chứa ký tự viết thường!"
          },
          {
            validator: (value: string) => {
              const regex = /[0-9]/;
              return regex.test(value);
            },
            errorMessage: "Mật khẩu phải chứa chữ số!"
          },
          {
            validator: (value: string) => {
              const regex = /[^a-zA-Z0-9]/;
              return regex.test(value);
            },
            errorMessage: "Mật khẩu phải chứa ký tự đặc biệt!"
          },
        ])
        .onSuccess((event: any) => {
          event.preventDefault();
          
          // Lấy giá trị từ form
          const phoneNumber = event.target.phoneNumber.value;
          const password = event.target.password.value;

          // Gọi API đăng nhập
          fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/login`, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              phoneNumber: phoneNumber,
              password: password
            }),
            credentials: "include"
          })
            .then(res => res.json())
            .then(data => {
              console.log("Login response data:", data);
              console.log("Full response:", JSON.stringify(data, null, 2));
              if (!data.success) {
                alert(data.message || "Đăng nhập thất bại");
              }
              if (data.success) {
                console.log("Login successful!");
                console.log("User data:", data.data);
                // Lưu thông tin user vào localStorage nếu có
                // fullName nằm ở data.data.user.fullName
                if (data.data && data.data.user && data.data.user.fullName) {
                  console.log("Saving fullName to localStorage:", data.data.user.fullName);
                  localStorage.setItem('userFullName', data.data.user.fullName);
                  // Dispatch custom event để Header cập nhật
                  window.dispatchEvent(new Event('userUpdated'));
                } else {
                  console.log("No fullName found in response data");
                  console.log("Data structure:", JSON.stringify(data, null, 2));
                }
                router.push("/");
              }
            })
            .catch(error => {
              console.error("Error:", error);
              alert("Có lỗi xảy ra khi đăng nhập");
            });
        });

      validatorRef.current = validator;
    }, 100);

    return () => {
      clearTimeout(timeoutId);
      if (validatorRef.current) {
        validatorRef.current.destroy();
        validatorRef.current = null;
      }
      document.querySelectorAll(".just-validate-error-label").forEach(el => el.remove());
    };
  }, []);

  return (
    <>
      <div className="flex">
        {/* Left */}
        <div className="hidden lg:block w-1/2 h-screen sticky top-0">
          <img
            src="/assets/images/bg-login.jpg"
            className="w-full h-full object-cover"
            alt="Background Login"
          />
        </div>
        {/* End Left */}

        {/* Right */}
        <div className="flex-1 h-screen overflow-y-hidden bg-white flex items-center justify-center py-[249px] pt-[185px]">
          <div className="w-full max-w-[436px] px-[16px]">

            <h2 className="font-[700] text-[33px] text-[#D70018] text-center mb-[40px]">
              Đăng nhập
            </h2>

            <form
              id="loginForm"
              className="flex flex-col gap-[24px] mb-8 w-full"
            >
              {inputFields && (
                inputFields.map((field) => (
                  <InputField
                    key={field.id}
                    label={field.label}
                    type={field.type}
                    id={field.id}
                    placeholder={field.placeholder}
                  />
                ))
              )}

              <div className="mt-2">
                <AppButton>Đăng nhập</AppButton>
                <div className="flex justify-center px-[12px] py-[16px] hover:underline decoration-[#3B82F6]">
                  <Link
                    className="font-medium text-base text-[#3B82F6]"
                    href={""}
                  >
                    Quên mật khẩu?
                  </Link>
                </div>
              </div>
            </form>

            <div className="mb-[70px] w-full">
              <div className="flex items-center gap-4 mb-6">
                <div className="flex-1 h-px bg-[#E4E4E7]"></div>
                <span className="text-sm text-[#71717A] whitespace-nowrap">
                  Hoặc đăng nhập bằng
                </span>
                <div className="flex-1 h-px bg-[#E4E4E7]"></div>
              </div>

              <div className="flex md:gap-[20px] gap-[10px] w-full items-center justify-center cursor-pointer">
                {socialButtons && (
                  socialButtons.map((social) => (
                    <SocialButton
                      key={social.label}
                      icon={social.icon}
                      label={social.label}
                    />
                  ))
                )}
              </div>
            </div>

            <div className="flex justify-center items-center gap-2 text-base">
              <span className="text-[#71717A]">
                Bạn chưa có tài khoản?
              </span>
              <div className="hover:underline decoration-[#D70018]">
                <Link
                  className="text-[#D70018] font-medium hover:underline cursor-pointer"
                  href={"/register"}
                >
                  Đăng ký ngay
                </Link>
              </div>
            </div>

          </div>
        </div>
        {/* End Right */}
      </div >
    </>
  )
}