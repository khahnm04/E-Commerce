"use client";
import { InputField } from "@/app/components/auth/InputField";
import { SocialButton } from "@/app/components/auth/SocialButton";
import { FaChevronLeft } from "react-icons/fa";
import Link from "next/link";
import { useEffect, useRef } from "react";
import { useRouter } from "next/navigation";
import JustValidate from "just-validate";

export default function RegisterPage() {
  const validatorRef = useRef<any>(null);
  const router = useRouter();

  const inputFieldInfos = [
    {
      id: "fullName",
      label: "Họ và tên",
      type: "text",
      placeholder: "Nhập họ tên"
    },
    {
      id: "dateOfBirth",
      label: "Ngày sinh",
      type: "date",
      placeholder: ""
    },
    {
      id: "phoneNumber",
      label: "Số điện thoại",
      type: "tel",
      placeholder: "Nhập số điện thoại"
    },
    {
      id: "email",
      label: "Email",
      type: "email",
      placeholder: "Nhập email"
    }
  ];

  const inputFieldPasswords = [
    {
      id: "password",
      label: "Mật khẩu",
      type: "password",
      placeholder: "Nhập mật khẩu của bạn"
    },
    {
      id: "confirmPassword",
      label: "Nhập lại mật khẩu",
      type: "password",
      placeholder: "Nhập lại mật khẩu của bạn"
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

      const validator = new JustValidate("#registerForm", {
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
        .addField("#fullName", [
          {
            rule: "required",
            errorMessage: "Vui lòng nhập họ và tên!"
          }
        ])
        .addField("#dateOfBirth", [
          {
            rule: "required",
            errorMessage: "Vui lòng chọn ngày sinh!"
          }
        ])
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
        .addField("#email", [
          {
            rule: "required",
            errorMessage: "Vui lòng nhập email!"
          },
          {
            rule: "email",
            errorMessage: "Email không đúng định dạng!"
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
        .addField('#confirmPassword', [
          {
            rule: "required",
            errorMessage: "Vui lòng nhập lại mật khẩu!"
          },
          {
            validator: (value: string) => {
              const passwordField = document.querySelector('#password') as HTMLInputElement;
              return passwordField && value === passwordField.value;
            },
            errorMessage: "Mật khẩu nhập lại không khớp!"
          },
        ])
        .onSuccess((event: any) => {
          event.preventDefault();
          
          // Lấy giá trị từ form
          const fullName = event.target.fullName.value;
          const dateOfBirth = event.target.dateOfBirth.value; // Date input đã trả về format yyyy-mm-dd
          const phoneNumber = event.target.phoneNumber.value;
          const email = event.target.email.value;
          const password = event.target.password.value;
          const confirmPassword = event.target.confirmPassword.value;

          // Gọi API đăng ký
          fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/register`, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              fullName: fullName,
              dateOfBirth: dateOfBirth,
              phoneNumber: phoneNumber,
              email: email,
              password: password,
              confirmPassword: confirmPassword
            }),
            credentials: "include"
          })
            .then(res => res.json())
            .then(data => {
              console.log(data);
              if (!data.success) {
                alert(data.message || "Đăng ký thất bại");
              }
              if (data.success) {
                router.push("/login");
              }
            })
            .catch(error => {
              console.error("Error:", error);
              alert("Có lỗi xảy ra khi đăng ký");
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
      <div className="w-full max-w-[800px] mx-auto px-4 sm:px-6 lg:px-8 py-[24px] md:py-[60px]">
        <h2 className="font-[700] text-[24px] sm:text-[28px] md:text-[33px] text-[#D70018] text-center mb-6 md:mb-[32px]">
          Đăng ký tài khoản để tiếp tục
        </h2>

        <div className="flex flex-col justify-center items-center gap-3 md:gap-[20px] mb-6 md:mb-[38px]">
          <p className="font-[400] text-[14px] md:text-[16px] text-[#71717a]">
            Đăng ký bằng tài khoản mạng xã hội
          </p>
          <div className="flex flex-col sm:flex-row gap-3 md:gap-[20px] w-full sm:w-auto">
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

        <div className="">
          <p className="font-[400] text-[14px] md:text-[16px] text-[#71717a] text-center mb-4 md:mb-[16px]">
            Hoặc điền thông tin sau
          </p>
          <form
            id="registerForm"
            className="flex flex-col gap-4 mb-6 md:mb-[32px] w-full"
          >
            <p className="font-[700] text-[16px] md:text-[18px] text-[#121214]">
              Thông tin cá nhân
            </p>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 md:gap-[24px] mb-3 md:mb-[14px]">
              {inputFieldInfos && (
                inputFieldInfos.map((field) => {
                  // Lấy ngày hôm nay để giới hạn không được chọn ngày trong tương lai
                  const today = new Date().toISOString().split('T')[0];
                  return (
                    <InputField
                      key={field.id}
                      label={field.label}
                      type={field.type}
                      id={field.id}
                      placeholder={field.placeholder}
                      max={field.id === "dateOfBirth" ? today : undefined}
                    />
                  );
                })
              )}
            </div>

            <p className="font-[700] text-[16px] md:text-[18px] text-[#121214]">
              Tạo mật khẩu
            </p>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 md:gap-[24px] mb-6 md:mb-[40px]">
              {inputFieldPasswords && (
                inputFieldPasswords.map((field) => (
                  <InputField
                    key={field.id}
                    label={field.label}
                    type={field.type}
                    id={field.id}
                    placeholder={field.placeholder}
                  />
                ))
              )}
            </div>

            <div className="flex flex-col sm:flex-row gap-4 md:gap-[24px]">
              <Link
                className="border border-[#CFCFD3] flex justify-center items-center gap-2 w-full sm:flex-1 px-4 py-3 rounded-[8px] hover:bg-gray-50 transition-colors"
                href={"/login"}
              >
                <FaChevronLeft className="text-[14px] md:text-[16px]" />
                <p className="font-[500] text-[14px] md:text-[16px] color-[#1D1D20]">
                  Quay lại đăng nhập
                </p>
              </Link>
              <button
                type="submit"
                className="border border-[#CFCFD3] bg-[#D70018] flex justify-center items-center gap-2 w-full sm:flex-1 px-4 py-3 rounded-[8px] hover:bg-[#b8001a] transition-colors"
              >
                <span className="font-[500] text-[14px] md:text-[16px] text-[#fff]">
                  Hoàn tất đăng ký
                </span>
              </button>
            </div>

          </form>
        </div>
      </div>
    </>
  )
}