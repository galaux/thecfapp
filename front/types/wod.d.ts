export type Movement = {
  id: string;
  name: string;
  reps: string;
  rx_male?: string;
  rx_female?: string;
  unit?: string;
};

export type Wod = {
  id: string;
  name: string;
  desc: string;
  movements: Movement[];
};
