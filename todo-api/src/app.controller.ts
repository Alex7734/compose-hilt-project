import { Controller, Get } from '@nestjs/common';
import { ApiTags } from '@nestjs/swagger';

@ApiTags('app')
@Controller()
export class AppController {

  @Get()
  getRoot() {
    return {
      name: 'Todo API',
      version: process.env.API_VERSION || '1.0.0'
    };
  }
}
