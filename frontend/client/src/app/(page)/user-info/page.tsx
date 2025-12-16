"use client";
import { useState } from "react";
import { UserHeader } from "@/app/components/user-info/UserHeader";
import { SidebarMenu } from "@/app/components/user-info/SidebarMenu";
import { OrderHistorySection } from "@/app/components/user-info/OrderHistorySection";
import { PersonalInfoSection } from "@/app/components/user-info/PersonalInfoSection";
import { AddressSection } from "@/app/components/user-info/AddressSection";
import { PasswordSection } from "@/app/components/user-info/PasswordSection";
import { LinkedAccountsSection } from "@/app/components/user-info/LinkedAccountsSection";
import { LogoutModal } from "@/app/components/user-info/LogoutModal";
import { AllAddressesModal } from "@/app/components/user-info/AllAddressesModal";
import {
  defaultUserStats,
  defaultPersonalInfo,
  defaultContactInfo,
  defaultAddresses,
  defaultOrders,
  orderTabs
} from "@/app/components/user-info/constants";

export default function UserInfoPage() {
  const [activeTab, setActiveTab] = useState("account");
  const [showLogoutModal, setShowLogoutModal] = useState(false);
  const [showAllAddresses, setShowAllAddresses] = useState(false);

  // TODO: Replace with API calls
  const userStats = defaultUserStats;
  const personalInfo = defaultPersonalInfo;
  const contactInfo = defaultContactInfo;
  const addresses = defaultAddresses;
  const orders = defaultOrders;

  return (
    <div className="container">
      <UserHeader
        name="Nguyễn Minh Khánh"
        phone="0387011685"
        userStats={userStats}
      />

      <div className="flex gap-[12px] mb-[70px] items-start">
        <SidebarMenu
          activeTab={activeTab}
          onTabChange={setActiveTab}
          onLogout={() => setShowLogoutModal(true)}
        />

        <div className="flex-1 flex flex-col gap-[12px]">
          {activeTab === "history" && (
            <OrderHistorySection orders={orders} orderTabs={orderTabs} />
          )}

          {activeTab === "account" && (
            <>
              <PersonalInfoSection
                personalInfo={personalInfo}
                contactInfo={contactInfo}
              />

              <AddressSection
                addresses={addresses}
                onShowAll={() => setShowAllAddresses(true)}
              />

              <div className="flex gap-[12px]">
                <PasswordSection />
                <LinkedAccountsSection />
              </div>
            </>
          )}
        </div>
      </div>

      <AllAddressesModal
        isOpen={showAllAddresses}
        addresses={addresses}
        onClose={() => setShowAllAddresses(false)}
      />

      <LogoutModal
        isOpen={showLogoutModal}
        onClose={() => setShowLogoutModal(false)}
      />
    </div>
  );
}
