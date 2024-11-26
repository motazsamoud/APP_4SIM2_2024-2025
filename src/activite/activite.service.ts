import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { ActiviteRecyclage } from './schemas/activite-recyclage.schema';

@Injectable()
export class ActiviteRecyclageService {
  constructor(
    @InjectModel(ActiviteRecyclage.name)
    private readonly activiteRecyclageModel: Model<ActiviteRecyclage>,
  ) {}

  async getActivites(): Promise<ActiviteRecyclage[]> {
    return this.activiteRecyclageModel.find().exec();
  }

  async addActivite(activite: Partial<ActiviteRecyclage>): Promise<ActiviteRecyclage> {
    return this.activiteRecyclageModel.create(activite);
  }

  async getActiviteById(id: string): Promise<ActiviteRecyclage> {
    const activite = await this.activiteRecyclageModel.findById(id).exec();
    if (!activite) {
      throw new NotFoundException('Activité non trouvée');
    }
    return activite;
  }

  async updateActivite(
    id: string,
    activiteBody: Partial<ActiviteRecyclage>,
  ): Promise<ActiviteRecyclage> {
    const activite = await this.getActiviteById(id);
    Object.assign(activite, activiteBody);
    return activite.save();
  }

  async getActivitiesByUserId(userId: string): Promise<ActiviteRecyclage[]> {
    return this.activiteRecyclageModel.find({ user: userId }).exec();
  }

  async deleteActivite(id: string): Promise<void> {
    const result = await this.activiteRecyclageModel.findByIdAndDelete(id).exec();
    if (!result) {
      throw new NotFoundException('Activité non trouvée');
    }
  }

  async getMaterialStatistics(): Promise<any> {
    return this.activiteRecyclageModel.aggregate([
      {
        $group: {
          _id: '$recyclableMaterial',
          count: { $sum: 1 },
        },
      },
    ]);
  }
}
