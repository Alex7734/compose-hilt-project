import { ApiProperty } from '@nestjs/swagger';

export class ApiResponseWrapperDto {
    @ApiProperty({ description: 'API version' })
    apiVersion: string;

    @ApiProperty({ description: 'Response timestamp' })
    timestamp: string;

    @ApiProperty({ description: 'Response data' })
    data: any;
} 