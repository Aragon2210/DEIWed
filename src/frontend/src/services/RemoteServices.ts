import AttendeeDto from '@/models/deiwed/AttendeeDto';
import DishDto from '@/models/deiwed/DishDto';
import OrderDto from '@/models/deiwed/OrderDto';
import SessionDto from '@/models/deiwed/SessionDto';
import DeiwedError from '@/models/error/DeiwedError';
import axios from 'axios';

const httpClient = axios.create();
httpClient.defaults.timeout = 50000;
httpClient.defaults.baseURL = process.env.VUE_APP_ROOT_API;
httpClient.defaults.headers.post['Content-Type'] = 'application/json';
httpClient.defaults.headers.common['Authorization'] = 'ist195606';

export default class RemoteServices {
  
  //================================Attendees============================

  static async createAttendee(attendee: AttendeeDto): Promise<AttendeeDto> {
    return httpClient
        .post("/attendees", attendee)
        .then((response) => response.data)
        .catch(async (error) => {
          throw new DeiwedError(
            await this.errorMessage(error),
            error.response.data.code
          );
        });
  }

  static async getAttendee(attendeeId: number): Promise<AttendeeDto> {
    return httpClient
      .get(`/attendees/${attendeeId}`)
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async getAttendees(): Promise<AttendeeDto[]> {
    return httpClient
      .get('/attendees')
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async getAttendeeSessions(attendeeId:number): Promise<SessionDto[]> {
    return httpClient
      .get(`/attendees/${attendeeId}/sessions`)
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async getNotAttendeeSessions(attendeeId:number): Promise<SessionDto[]> {
    return httpClient
      .get(`/attendees/${attendeeId}/not/sessions`)
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async updateAttendee(attendeeId: number, attendee: AttendeeDto): Promise<AttendeeDto> {
    return httpClient
        .put(`/attendees/${attendeeId}`, attendee)
        .then((response) => response.data)
        .catch(async (error) => {
          throw new DeiwedError(
            await this.errorMessage(error),
            error.response.data.code
          );
        });
  }

  static async addAttendeeToSession(attendeeId: number, sessionId: number) {
    return httpClient
        .put(`/attendees/${attendeeId}/addSession/${sessionId}`)
        .catch(async (error) => {
          throw new DeiwedError(
            await this.errorMessage(error),
            error.response.data.code
          );
        });
  }

  static async removeAttendeeFromSession(attendeeId: number, sessionId: number) {
    return httpClient
        .put(`/attendees/${attendeeId}/removeSession/${sessionId}`)
        .catch(async (error) => {
          throw new DeiwedError(
            await this.errorMessage(error),
            error.response.data.code
          );
        });
  }

  static async deleteAttendee(attendeeId: number) {
    return httpClient
        .delete(`/attendees/${attendeeId}`)
        .catch(async (error) => {
          throw new DeiwedError(
            await this.errorMessage(error),
            error.response.data.code
          );
        });
  }

  //================================Sessions============================

  static async createSession(session: SessionDto): Promise<SessionDto> {
    return httpClient
        .post('/sessions', session)
        .then((response) => response.data)
        .catch(async (error) => {
          throw new DeiwedError(
            await this.errorMessage(error),
            error.response.data.code
          );
        });
  }

  static async getSession(sessionId: number): Promise<SessionDto> {
    return httpClient
      .get(`/sessions/${sessionId}`)
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async getSessions(): Promise<SessionDto[]> {
    return httpClient
      .get('/sessions')
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  static async updateSession(sessionId: number, session: SessionDto): Promise<SessionDto> {
    return httpClient
        .put(`/sessions/${sessionId}`, session)
        .then((response) => response.data)
        .catch(async (error) => {
          throw new DeiwedError(
            await this.errorMessage(error),
            error.response.data.code
          );
        });
  }

  static async deleteSession(sessionId: number) {
    return httpClient
        .delete(`/sessions/${sessionId}`)
        .catch(async (error) => {
          throw new DeiwedError(
            await this.errorMessage(error),
            error.response.data.code
          );
        });
  }

  //=============================Dishes/Orders==========================

  static async createOrder(order: OrderDto, date: string): Promise<OrderDto> {
    return httpClient
        .post(`https://eindhoven.rnl.tecnico.ulisboa.pt/food-store/docs/#post-/orders/${date}`, order)
        .then((response) => response.data)
        .catch(async (error) => {
          throw new DeiwedError(
            await this.errorMessage(error),
            error.response.data.code
          );
        });
  }

  static async getDishes(): Promise<DishDto[]> {
    return httpClient
      .get('https://eindhoven.rnl.tecnico.ulisboa.pt/food-store/docs/#get-/dishes')
      .then((response) => response.data)
      .catch(async (error) => {
        throw new DeiwedError(
          await this.errorMessage(error),
          error.response.data.code
        );
      });
  }

  
  static async errorMessage(error: any): Promise<string> {
    if (error.message === 'Network Error') {
      return 'Unable to connect to server';
    } else if (error.message.split(' ')[0] === 'timeout') {
      return 'Request timeout - Server took too long to respond';
    } else if (error.response?.data?.message) {
      return error.response.data.message;
    } else {
      return 'Unknown Error - Contact admin';
    }
  }
}
