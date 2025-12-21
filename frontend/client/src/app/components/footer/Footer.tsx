import React from 'react';
import {
  Phone, MapPin, Mail, Facebook, Youtube, Instagram,
  ShieldCheck, ChevronRight, CheckCircle2, QrCode, Smartphone,
  CreditCard, Truck
} from 'lucide-react';

export default function Footer() {
  return (
    // Bỏ border đỏ, chỉ dùng border xám nhẹ tinh tế
    <footer className="bg-white border-t border-gray-200 pt-[60px]">

      <div className="flex justify-center pb-[40px]">
        <div className="w-[1232px] grid grid-cols-4 gap-[40px]">

          {/* --- CỘT 1: TỔNG ĐÀI & HỖ TRỢ --- */}
          <div>
            <h3 className="text-[14px] font-bold text-gray-900 mb-[16px] uppercase">Tổng đài hỗ trợ (Miễn phí)</h3>
            <ul className="flex flex-col gap-[10px] text-[12px] text-gray-600 mb-[24px]">
              <li className="flex justify-between items-center">
                <span>Gọi mua hàng (8:00 - 21:00)</span>
                <span className="font-bold text-[14px] text-gray-900">1800.2097</span>
              </li>
              <li className="flex justify-between items-center">
                <span>Khiếu nại (8:00 - 21:00)</span>
                <span className="font-bold text-[14px] text-gray-900">1800.2063</span>
              </li>
              <li className="flex justify-between items-center">
                <span>Bảo hành (8:00 - 21:00)</span>
                <span className="font-bold text-[14px] text-gray-900">1800.2064</span>
              </li>
            </ul>

            <div className="flex items-center gap-2 mb-3">
              <CreditCard size={16} className="text-gray-400" />
              <h3 className="text-[14px] font-bold text-gray-900">Phương thức thanh toán</h3>
            </div>

            {/* Giả lập Logo Thanh toán (Trông như ảnh thật) */}
            <div className="grid grid-cols-4 gap-2">
              <div className="h-[24px] bg-white border border-gray-200 rounded-[3px] flex items-center justify-center shadow-sm" title="Apple Pay">
                <span className="text-[9px] font-bold text-black"> Pay</span>
              </div>
              <div className="h-[24px] bg-[#1a1f71] rounded-[3px] flex items-center justify-center shadow-sm" title="Visa">
                <span className="text-[9px] font-bold text-white uppercase italic">Visa</span>
              </div>
              <div className="h-[24px] bg-[#eb001b] rounded-[3px] flex items-center justify-center shadow-sm" title="Mastercard">
                <span className="text-[8px] font-bold text-white uppercase">Master</span>
              </div>
              <div className="h-[24px] bg-[#005ba1] rounded-[3px] flex items-center justify-center shadow-sm" title="JCB">
                <span className="text-[8px] font-bold text-white uppercase">JCB</span>
              </div>
              <div className="h-[24px] bg-[#a50064] rounded-[3px] flex items-center justify-center shadow-sm" title="MoMo">
                <span className="text-[8px] font-bold text-white">MoMo</span>
              </div>
              <div className="h-[24px] bg-[#0068ff] rounded-[3px] flex items-center justify-center shadow-sm" title="ZaloPay">
                <span className="text-[8px] font-bold text-white">ZaloPay</span>
              </div>
              <div className="h-[24px] bg-white border border-gray-200 rounded-[3px] flex items-center justify-center shadow-sm" title="Tiền mặt">
                <span className="text-[8px] font-bold text-gray-600">CASH</span>
              </div>
              <div className="h-[24px] bg-[#ed1c24] rounded-[3px] flex items-center justify-center shadow-sm" title="VNPAY">
                <span className="text-[8px] font-bold text-white italic">VNPAY</span>
              </div>
            </div>
          </div>

          {/* --- CỘT 2: THÔNG TIN KHÁC --- */}
          <div>
            <h3 className="text-[14px] font-bold text-gray-900 mb-[16px] uppercase">Thông tin và chính sách</h3>
            <ul className="flex flex-col gap-[8px] text-[12px] text-gray-500">
              {['Mua hàng và thanh toán Online', 'Mua hàng trả góp Online', 'Chính sách giao hàng', 'Tra cứu thông tin bảo hành', 'Tra cứu hoá đơn điện tử', 'Thông tin hoá đơn mua hàng', 'Trung tâm bảo hành chính hãng', 'Quy định về việc sao lưu dữ liệu'].map((item, idx) => (
                <li key={idx}>
                  <a href="#" className="hover:text-black hover:underline transition-colors block">{item}</a>
                </li>
              ))}
            </ul>
          </div>

          {/* --- CỘT 3: DỊCH VỤ & ĐỐI TÁC --- */}
          <div>
            <h3 className="text-[14px] font-bold text-gray-900 mb-[16px] uppercase">Dịch vụ và thông tin khác</h3>
            <ul className="flex flex-col gap-[8px] text-[12px] text-gray-500 mb-[24px]">
              {['Khách hàng doanh nghiệp (B2B)', 'Ưu đãi thanh toán', 'Quy chế hoạt động', 'Chính sách bảo mật thông tin', 'Tin tuyển dụng', 'Xem bản mobile'].map((item, idx) => (
                <li key={idx}>
                  <a href="#" className="hover:text-black hover:underline transition-colors block">{item}</a>
                </li>
              ))}
            </ul>

            <div className="flex items-center gap-2 mb-3">
              <Truck size={16} className="text-gray-400" />
              <h3 className="text-[14px] font-bold text-gray-900">Đối tác vận chuyển</h3>
            </div>
            {/* Giả lập Logo Vận chuyển */}
            <div className="grid grid-cols-4 gap-2">
              <div className="h-[24px] bg-white border border-gray-200 rounded-[3px] flex items-center justify-center shadow-sm">
                <span className="text-[8px] font-bold text-orange-500">ShopeeExp</span>
              </div>
              <div className="h-[24px] bg-white border border-gray-200 rounded-[3px] flex items-center justify-center shadow-sm">
                <span className="text-[8px] font-bold text-green-600">GHTK</span>
              </div>
              <div className="h-[24px] bg-white border border-gray-200 rounded-[3px] flex items-center justify-center shadow-sm">
                <span className="text-[8px] font-bold text-blue-600">Ahamove</span>
              </div>
              <div className="h-[24px] bg-white border border-gray-200 rounded-[3px] flex items-center justify-center shadow-sm">
                <span className="text-[8px] font-bold text-red-600">J&T</span>
              </div>
            </div>
          </div>

          {/* --- CỘT 4: KẾT NỐI & TẢI APP --- */}
          <div>
            <h3 className="text-[14px] font-bold text-gray-900 mb-[16px] uppercase">Kết nối với chúng tôi</h3>
            <div className="flex gap-[12px] mb-[24px]">
              <a href="#" className="w-[32px] h-[32px] bg-[#1877f2] rounded-[4px] flex items-center justify-center text-white hover:opacity-90 transition-opacity">
                <Facebook size={18} />
              </a>
              <a href="#" className="w-[32px] h-[32px] bg-[#ff0000] rounded-[4px] flex items-center justify-center text-white hover:opacity-90 transition-opacity">
                <Youtube size={18} />
              </a>
              <a href="#" className="w-[32px] h-[32px] bg-black rounded-[4px] flex items-center justify-center text-white hover:opacity-90 transition-opacity">
                <Instagram size={18} />
              </a>
            </div>

            <h3 className="text-[14px] font-bold text-gray-900 mb-[12px] uppercase">Tải ứng dụng</h3>
            <div className="flex gap-[12px] items-start">
              {/* QR Code giả lập */}
              <div className="bg-white p-1 border border-gray-200 rounded-[4px] shadow-sm">
                <QrCode size={60} className="text-gray-800" />
              </div>
              <div className="flex flex-col gap-2">
                <div className="flex items-center gap-1 text-[11px] text-gray-600 hover:text-red-600 cursor-pointer">
                  <Smartphone size={14} /> App Store
                </div>
                <div className="flex items-center gap-1 text-[11px] text-gray-600 hover:text-red-600 cursor-pointer">
                  <Smartphone size={14} /> Google Play
                </div>
              </div>
            </div>

            <div className="mt-[20px]">
              <div className="w-[140px] h-[45px] relative rounded-[4px] overflow-hidden group cursor-pointer shadow-sm">
                {/* Giả lập ảnh Bộ Công Thương chi tiết hơn */}
                <div className="absolute inset-0 bg-gradient-to-r from-blue-700 to-blue-900"></div>
                <div className="absolute top-1 left-1 bg-red-600 rounded-full p-[2px]">
                  <CheckCircle2 size={10} className="text-white" />
                </div>
                <div className="absolute inset-0 flex flex-col items-center justify-center text-white pl-3">
                  <span className="text-[8px] font-bold uppercase leading-none mb-[2px]">Đã thông báo</span>
                  <span className="text-[9px] font-bold leading-none text-yellow-400">Bộ Công Thương</span>
                </div>
              </div>
            </div>
          </div>

        </div>
      </div>

      {/* --- FOOTER BOTTOM: LEGAL --- */}
      <div className="bg-[#f8f9fa] py-[16px] border-t border-gray-200">
        <div className="flex justify-center">
          <div className="w-[1232px] text-[10px] text-gray-500 leading-relaxed text-center">
            <p className="font-semibold mb-1 text-gray-600">Công ty TNHH Thương mại và Dịch vụ Kỹ thuật DIỆU PHÚC - Thành viên của Tập đoàn CellphoneS</p>
            <p>Giấy chứng nhận Đăng ký Kinh doanh số 0316172372 do Sở Kế hoạch và Đầu tư Thành phố Hồ Chí Minh cấp ngày 02/03/2020.</p>
            <p>Địa chỉ: 350-352 Võ Văn Kiệt, Phường Cô Giang, Quận 1, Thành phố Hồ Chí Minh, Việt Nam. Điện thoại: 028.7108.9666. Email: cskh@cellphones.com.vn</p>
            <p className="mt-2">© 2014 - 2025 Công ty TNHH Thương mại và Dịch vụ Kỹ thuật Diệu Phúc. Bảo lưu mọi quyền.</p>
          </div>
        </div>
      </div>

    </footer>
  );
}