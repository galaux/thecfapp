export type IWorkout = {
  id: string
  name: string
  description: string
  scoringType: string // FIXME use enum
}

export type ICommunity = {
  id: string
  name: string
  wods: IWod[]
}

export type IScore = {
  id: string
  value: number
}

export type IUser = {
  id: string
  name: string
}

export type IWod = {
  id: string
  date: Date
  workout: IWorkout
  scores: IScore[]
  community: ICommunity
}
