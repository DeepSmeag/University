import { MyPhoto } from "../hooks/usePhotos";

export interface ItemProps {
  _id?: string;
  text: string;
  desc: string;
  photo: MyPhoto;
  myLat: number;
  myLng: number;
}
