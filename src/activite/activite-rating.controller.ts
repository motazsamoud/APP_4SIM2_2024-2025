import { Controller, Post, Body, HttpException, HttpStatus } from '@nestjs/common';
import { ActiviteRatingService } from './activite-rating.service';
import { ActiviteRating } from './schemas/activite-rating.shema';

@Controller('activite-rating')
export class ActiviteRatingController {
  constructor(private readonly ratingService: ActiviteRatingService) {}

  @Post()
  async createRating(
    @Body()
    body: {
      rating: number;
      comment?: string;
      user: string;
      activite: string;
    },
  ): Promise<ActiviteRating> {
    try {
      return await this.ratingService.addRatingActivite(body);
    } catch (error) {
      throw new HttpException(error.message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
