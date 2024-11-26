import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { ActiviteRating } from './schemas/activite-rating.shema';

@Injectable()
export class ActiviteRatingService {
  constructor(
    @InjectModel(ActiviteRating.name)
    private readonly activiteRatingModel: Model<ActiviteRating>,
  ) {}

  async addRatingActivite({
    rating,
    comment,
    user,
    activite,
  }: {
    rating: number;
    comment?: string;
    user: string;
    activite: string;
  }): Promise<ActiviteRating> {
    return this.activiteRatingModel.create({ rating, comment, user, activite });
  }
}
