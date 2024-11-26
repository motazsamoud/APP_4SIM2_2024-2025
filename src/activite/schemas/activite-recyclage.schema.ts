import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document, Types } from 'mongoose';

@Schema()
export class ActiviteRecyclage extends Document {
  @Prop({ type: Types.ObjectId, ref: 'ActivityID' })
  ActivityID: Types.ObjectId;

  @Prop({ type: Date, required: true })
  dateAndTime: Date;

  @Prop({ type: Types.ObjectId, ref: 'User' })
  user: Types.ObjectId;

  @Prop({
    type: String,
    enum: ['plastic', 'paper', 'glass', 'metal', 'other'],
    required: true,
  })
  recyclableMaterial: string;

  @Prop({ type: Number, required: true })
  quantity: number;

  @Prop({ type: String, required: false })
  image: string;
}

export const ActiviteRecyclageSchema = SchemaFactory.createForClass(ActiviteRecyclage);
