import {
  Controller,
  Get,
  Post,
  Put,
  Delete,
  Param,
  Body,
  HttpException,
  HttpStatus,
} from '@nestjs/common';
import { ActiviteRecyclageService } from './activite.service';
import { ActiviteRecyclage } from './schemas/activite-recyclage.schema';

@Controller('/activite')
export class ActiviteRecyclageController {
  constructor(private readonly activiteService: ActiviteRecyclageService) {}

  @Get()
  async getActivites(): Promise<ActiviteRecyclage[]> {
    try {
      return await this.activiteService.getActivites();
    } catch (error) {
      throw new HttpException(error.message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Post("/add")
  async addActivite(@Body() body: Partial<ActiviteRecyclage>): Promise<ActiviteRecyclage> {
    try {
      return await this.activiteService.addActivite(body);
    } catch (error) {
      throw new HttpException(error.message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Put("/update/:id")
  async updateActivite(
    @Param('id') id: string,
    @Body() body: Partial<ActiviteRecyclage>,
  ): Promise<ActiviteRecyclage> {
    try {
      return await this.activiteService.updateActivite(id, body);
    } catch (error) {
      throw new HttpException(error.message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Get(':id')
  async getActivite(@Param('id') id: string): Promise<ActiviteRecyclage> {
    try {
      return await this.activiteService.getActiviteById(id);
    } catch (error) {
      throw new HttpException(error.message, HttpStatus.NOT_FOUND);
    }
  }

  @Delete("/delete/:id")
  async deleteActivite(@Param('id') id: string): Promise<{ message: string }> {
    try {
      await this.activiteService.deleteActivite(id);
      return { message: 'Activite deleted' };
    } catch (error) {
      throw new HttpException(error.message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Get('/get/:userId')
  async getActivitiesByUser(@Param('userId') userId: string): Promise<ActiviteRecyclage[]> {
    try {
      return await this.activiteService.getActivitiesByUserId(userId);
    } catch (error) {
      throw new HttpException(error.message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Get("/statistics/material")
  async getMaterialStatistics(): Promise<any> {
    try {
      return await this.activiteService.getMaterialStatistics();
    } catch (error) {
      throw new HttpException(error.message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
