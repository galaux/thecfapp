import { Spinner } from '@chakra-ui/react'
import React, { PropsWithChildren } from 'react'

interface Props {
  loading: boolean
}

export const LoadingOr: React.FC<PropsWithChildren<Props>> = (props) =>
  props.loading ? <Spinner /> : <>{props.children}</>
