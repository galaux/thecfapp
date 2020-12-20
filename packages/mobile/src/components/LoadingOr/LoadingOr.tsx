import React, { PropsWithChildren } from 'react'
import { Spinner } from '@chakra-ui/react'

interface Props {
  loading: boolean
}

export const LoadingOr: React.FC<PropsWithChildren<Props>> = (props) =>
  props.loading ? <Spinner /> : <>{props.children}</>
