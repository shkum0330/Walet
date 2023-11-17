import { common } from './commonApiInterface';

export interface Transactionsdata {
  id: string;
  recipient: string;
  businessCategory: string | null;
  transactionType: string;
  paymentAmount: string;
  balance: string;
  transactionTime: string;
}

export interface TrasnactionsAPIResponse extends common {
  data: {
    transactions: Transactionsdata[];
  };
}

export interface TrasnactionsAllAPIResponse extends common {
  data: {
    transactions: Transactionsdata[];
  };
}
