export interface ApiErrorResponse {
  timestamp: string;
  status: number;
  error: string;
  trace: string;
  message: string;
  path: string;
}
