import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { ActiviteRecyclage, ActiviteRecyclageSchema } from './schemas/activite-recyclage.schema';
import { ActiviteRating, ActiviteRatingSchema } from './schemas/activite-rating.shema';
import { ActiviteRecyclageController } from './activite.controller';
import { ActiviteRecyclageService } from './activite.service';

@Module({
  imports: [
    MongooseModule.forFeature([
      { name: ActiviteRecyclage.name, schema: ActiviteRecyclageSchema },
      { name: ActiviteRating.name, schema: ActiviteRatingSchema },
    ]),
  ],
  controllers: [ActiviteRecyclageController],
  providers: [ActiviteRecyclageService],
})
export class ActiviteModule {}
