import axios from 'axios';
import { baseUrl, config, withLogs } from '../core';

const authUrl = `http://${baseUrl}/api/auth/login`;
const logoutUrl = `http://${baseUrl}/api/auth/logout`;
export interface AuthProps {
  token: string;
}

export const login: (username?: string, password?: string) => Promise<AuthProps> = (username, password) => {
  return withLogs(axios.post(authUrl, { username, password }, config), 'login');
}
export const logout: (username?: string, password?: string) => Promise<AuthProps> = (username, password) => {
  return withLogs(axios.post(logoutUrl, { username, password }, config), 'logout');
}
