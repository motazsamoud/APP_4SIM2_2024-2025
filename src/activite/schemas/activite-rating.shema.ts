import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document, Types } from 'mongoose';
import { ActiviteRecyclage } from './activite-recyclage.schema';

@Schema()
export class ActiviteRating extends Document {
  @Prop({ type: Types.ObjectId, ref: 'ActiviteRecyclage' })
  activite: ActiviteRecyclage;

  @Prop({ type: Types.ObjectId, ref: 'User' })
  user: Types.ObjectId;

  @Prop({ type: Number, min: 1, max: 5 })
  rating: number;

  @Prop({ type: String })
  comment: string;
}

export const ActiviteRatingSchema = SchemaFactory.createForClass(ActiviteRating);
