export interface Address {
  name: string;
  phone: string;
  address: string;
  isDefault: boolean;
  type: string;
}

export interface Order {
  orderId: string;
  orderDate: string;
  status: string;
  statusColor: string;
  product: {
    name: string;
    image: string;
    price: string;
    vat: string;
  };
  total: string;
}

export interface UserStat {
  icon: string;
  value: string;
  description: string;
}

export interface InfoItem {
  label: string;
  value: string;
}

export interface OrderTab {
  id: string;
  label: string;
}

