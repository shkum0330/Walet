import axios from 'axios';
import {
  TrasnactionsAPIResponse,
  TrasnactionsAllAPIResponse,
} from '../../interface/api/logApiinterface';
import { LOG_URI } from '../apiInfo';

export async function TransactionLogAllAPI(token: string) {
  const TrasnactionsLogAllURI = `${LOG_URI}/transaction/all`;
  if (token) {
    try {
      const response = await axios.get<TrasnactionsAllAPIResponse>(
        TrasnactionsLogAllURI,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      return response.data.data;
    } catch (error) {}
  }
}

export async function TransactionLogAPI(token: string) {
  const TrasnactionsLogURI = `${LOG_URI}/transaction`;
  if (token) {
    try {
      const response = await axios.get<TrasnactionsAPIResponse>(
        TrasnactionsLogURI,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      return response.data.data;
    } catch (error) {}
  }
}
